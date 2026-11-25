import { useCallback } from 'react'
import { getProject } from '../services/projects'
import { useAsync } from './useAsync'

export function useProject(projectId: string | undefined) {
  const load = useCallback(
    (signal: AbortSignal) =>
      projectId ? getProject(projectId, signal) : Promise.resolve(null),
    [projectId],
  )
  const { data, error, loading, reload } = useAsync(load, [projectId])

  return { project: data, error, loading, reload }
}
