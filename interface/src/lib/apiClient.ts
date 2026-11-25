import { getAccessToken } from '../auth/session'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:9000/api'

export class ApiError extends Error {
  readonly status: number
  readonly code: string

  constructor(status: number, code: string, message: string) {
    super(message)
    this.name = 'ApiError'
    this.status = status
    this.code = code
  }
}

export function toApiError(error: unknown): ApiError {
  if (error instanceof ApiError) return error
  if (error instanceof Error) return new ApiError(0, 'UNKNOWN_ERROR', error.message)
  return new ApiError(0, 'UNKNOWN_ERROR', 'Erreur inconnue')
}

export async function apiRequest<T>(path: string, init: RequestInit = {}): Promise<T> {
  const headers = new Headers(init.headers)
  headers.set('Accept', 'application/json')
  if (init.body !== undefined) {
    headers.set('Content-Type', 'application/json')
  }

  const token = getAccessToken()
  if (token) {
    headers.set('Authorization', `Bearer ${token}`)
  }

  let response: Response
  try {
    response = await fetch(`${API_BASE_URL}${path}`, { ...init, headers })
  } catch (error) {
    if (error instanceof DOMException && error.name === 'AbortError') throw error
    throw new ApiError(0, 'NETWORK_ERROR', 'Impossible de joindre la passerelle API')
  }

  if (!response.ok) {
    let body: { code?: string; message?: string } = {}
    try {
      body = (await response.json()) as { code?: string; message?: string }
    } catch {
      // La passerelle n'a pas renvoye de corps JSON exploitable.
    }
    throw new ApiError(
      response.status,
      body.code ?? 'HTTP_ERROR',
      body.message ?? `Erreur HTTP ${response.status}`,
    )
  }

  if (response.status === 204) {
    return undefined as T
  }
  return (await response.json()) as T
}
