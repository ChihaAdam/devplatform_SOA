import { useCallback } from 'react'
import type { CreateProjectPayload, CreateTaskPayload } from '../types/domain'
import { createProject, createTask, updateTaskState } from '../services/projects'
import { useMutation } from './useMutation'

export function useCreateProject() {
  const create = useCallback((payload: CreateProjectPayload) => createProject(payload), [])
  return useMutation(create)
}

export function useCreateTask(projectId: string) {
  const create = useCallback(
    (payload: CreateTaskPayload) => createTask(projectId, payload),
    [projectId],
  )
  return useMutation(create)
}

export function useUpdateTaskState(projectId: string) {
  const update = useCallback(
    (taskId: string, etat: string) => updateTaskState(projectId, taskId, etat),
    [projectId],
  )
  return useMutation(update)
}
