import { useCallback } from 'react'
import { listProjects } from '../services/projects'
import { useAsync } from './useAsync'

export function useProjects() {
  const load = useCallback((signal: AbortSignal) => listProjects(signal), [])
  const { data, error, loading, reload } = useAsync(load, [])

  return { projects: data ?? [], error, loading, reload }
}
