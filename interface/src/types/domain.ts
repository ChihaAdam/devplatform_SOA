/** Modele cote interface : il reflete les champs renvoyes par la passerelle. */

export interface Developer {
  id: string
  nom: string
  email: string
  role: string
}

export interface Task {
  id: string
  titre: string
  tempsEstime: number
  etat: string
  priorite: string
  type: string
}

export interface Project {
  id: string
  nom: string
  statut: string
  lang: string
  developpeurs: Developer[]
  taches: Task[]
}

export interface CreateProjectPayload {
  nom: string
  statut?: string
  lang: string
}

export interface CreateTaskPayload {
  titre: string
  tempsEstime?: number
  etat?: string
  priorite?: string
  type?: string
}

export const PROJECT_STATUSES = ['A_FAIRE', 'EN_COURS', 'REVUE', 'TERMINE'] as const
export const TASK_STATES = ['A_FAIRE', 'EN_COURS', 'REVUE', 'TERMINE'] as const
export const TASK_PRIORITIES = ['HAUTE', 'MOYENNE', 'BASSE'] as const
export const TASK_TYPES = ['FONCTIONNALITE', 'TACHE', 'BUG'] as const

const LABELS: Record<string, string> = {
  A_FAIRE: 'À faire',
  EN_COURS: 'En cours',
  REVUE: 'En revue',
  TERMINE: 'Terminé',
  HAUTE: 'Haute',
  MOYENNE: 'Moyenne',
  BASSE: 'Basse',
  FONCTIONNALITE: 'Fonctionnalité',
  TACHE: 'Tâche',
  BUG: 'Bug',
  BACKEND: 'Backend',
  FRONTEND: 'Frontend',
  fr: 'Français',
  en: 'Anglais',
}

export function formatLabel(value: string | undefined | null): string {
  if (!value) return '—'
  return LABELS[value] ?? value.replaceAll('_', ' ').toLowerCase()
}
