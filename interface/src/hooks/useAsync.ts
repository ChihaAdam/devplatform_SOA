import { useCallback, useEffect, useState } from 'react'
import { toApiError, type ApiError } from '../lib/apiClient'

interface AsyncResult<T> {
  key: string
  data: T
  error: ApiError | null
}

/**
 * Lecture annulable partagee par les pages. La cle de requete permet de faire
 * passer `loading` a true des que les dependances changent, sans setState dans un effet.
 */
export function useAsync<T>(load: (signal: AbortSignal) => Promise<T>, deps: unknown[]) {
  const [reloadToken, setReloadToken] = useState(0)
  const requestKey = `${reloadToken}|${deps.map((dependency) => String(dependency)).join('|')}`
  const [result, setResult] = useState<AsyncResult<T | null>>({
    key: '',
    data: null,
    error: null,
  })

  useEffect(() => {
    const controller = new AbortController()

    load(controller.signal)
      .then((data) => setResult({ key: requestKey, data, error: null }))
      .catch((error: unknown) => {
        if (controller.signal.aborted) return
        setResult({ key: requestKey, data: null, error: toApiError(error) })
      })

    return () => controller.abort()
    // `load` est volontairement pilote par `requestKey`.
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [requestKey])

  const reload = useCallback(() => setReloadToken((value) => value + 1), [])

  return {
    data: result.data,
    error: result.error,
    loading: result.key !== requestKey,
    reload,
  }
}
