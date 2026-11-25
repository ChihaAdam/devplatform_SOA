import { apiRequest } from '../lib/apiClient'
import type {
  CreateProjectPayload,
  CreateTaskPayload,
  Project,
  Task,
} from '../types/domain'

export function listProjects(signal?: AbortSignal): Promise<Project[]> {
  return apiRequest<Project[]>('/projects', { signal })
}

export function getProject(id: string, signal?: AbortSignal): Promise<Project> {
  return apiRequest<Project>(`/projects/${encodeURIComponent(id)}`, { signal })
}

export function createProject(payload: CreateProjectPayload, signal?: AbortSignal): Promise<Project> {
  return apiRequest<Project>('/projects', {
    method: 'POST',
    body: JSON.stringify(payload),
    signal,
  })
}

export function createTask(
  projectId: string,
  payload: CreateTaskPayload,
  signal?: AbortSignal,
): Promise<Project> {
  return apiRequest<Project>(`/projects/${encodeURIComponent(projectId)}/tasks`, {
    method: 'POST',
    body: JSON.stringify(payload),
    signal,
  })
}

export function updateTaskState(
  projectId: string,
  taskId: string,
  etat: string,
  signal?: AbortSignal,
): Promise<Task> {
  return apiRequest<Task>(`/projects/${encodeURIComponent(projectId)}/tasks/${encodeURIComponent(taskId)}`, {
    method: 'PUT',
    body: JSON.stringify({ etat }),
    signal,
  })
}
