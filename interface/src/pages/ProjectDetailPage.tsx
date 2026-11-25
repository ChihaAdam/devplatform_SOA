import { useState, type FormEvent } from 'react'
import { useParams } from 'react-router-dom'
import { Badge } from '../components/Badge'
import { Button } from '../components/Button'
import { ErrorBlock, LoadingBlock } from '../components/Feedback'
import { Field } from '../components/Field'
import { Modal } from '../components/Modal'
import { PageHeader } from '../components/PageHeader'
import { TaskTable } from '../components/TaskTable'
import { TeamList } from '../components/TeamList'
import { useCreateTask, useUpdateTaskState } from '../hooks/useProjectMutations'
import { useProject } from '../hooks/useProject'
import { ApiError } from '../lib/apiClient'
import {
  TASK_PRIORITIES,
  TASK_TYPES,
  formatLabel,
  type Task,
} from '../types/domain'

export function ProjectDetailPage() {
  const { projectId } = useParams<{ projectId: string }>()
  const { project, error, loading, reload } = useProject(projectId)
  const { run: create, pending, error: createError } = useCreateTask(projectId ?? '')
  const { run: update, error: updateError } = useUpdateTaskState(projectId ?? '')
  const [modalOpen, setModalOpen] = useState(false)
  const [titre, setTitre] = useState('')
  const [tempsEstime, setTempsEstime] = useState('')
  const [type, setType] = useState<string>('FONCTIONNALITE')
  const [priorite, setPriorite] = useState<string>('MOYENNE')

  const closeModal = () => {
    setModalOpen(false)
    setTitre('')
    setTempsEstime('')
    setType('FONCTIONNALITE')
    setPriorite('MOYENNE')
  }

  const handleCreate = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    const result = await create({
      titre,
      tempsEstime: tempsEstime === '' ? 0 : Number(tempsEstime),
      type,
      priorite,
    })
    if (result) {
      closeModal()
      reload()
    }
  }

  const handleStateChange = async (task: Task, etat: string) => {
    if (etat === task.etat) return
    const result = await update(task.id, etat)
    if (result) reload()
  }

  if (loading) return <LoadingBlock />
  if (error) return <ErrorBlock error={error} onRetry={reload} />
  if (!project) {
    return <ErrorBlock error={new ApiError(404, 'NOT_FOUND', 'Projet introuvable')} />
  }

  return (
    <>
      <PageHeader
        title={project.nom}
        description={`${project.id} · ${formatLabel(project.lang)}`}
        breadcrumbs={[
          { label: 'Projets', to: '/projects' },
          { label: project.nom },
        ]}
        actions={<Badge value={project.statut} />}
      />

      <div className="detail-layout">
        <section className="panel">
          <div className="panel__header">
            <div>
              <h2>Tâches</h2>
              <p>{project.taches.length} tâche{project.taches.length > 1 ? 's' : ''} dans ce projet</p>
            </div>
            <Button onClick={() => setModalOpen(true)}>+ Nouvelle tâche</Button>
          </div>
          {updateError && (
            <p className="form-error form-error--inline" role="alert">
              {updateError.message}
            </p>
          )}
          <TaskTable tasks={project.taches} onStateChange={handleStateChange} />
        </section>

        <aside className="panel panel--side">
          <div className="panel__header">
            <h2>Équipe</h2>
          </div>
          <TeamList developers={project.developpeurs} />
        </aside>
      </div>

      {modalOpen && (
        <Modal
          title="Nouvelle tâche"
          submitLabel="Créer la tâche"
          pending={pending}
          error={createError}
          onClose={closeModal}
          onSubmit={handleCreate}
        >
          <Field label="Titre" htmlFor="tache-titre">
            <input
              id="tache-titre"
              className="input"
              value={titre}
              onChange={(event) => setTitre(event.target.value)}
              placeholder="Ex. Écrire les tests d'intégration"
              required
              autoFocus
            />
          </Field>
          <Field label="Temps estimé (heures)" htmlFor="tache-temps">
            <input
              id="tache-temps"
              className="input"
              type="number"
              min="0"
              value={tempsEstime}
              onChange={(event) => setTempsEstime(event.target.value)}
              placeholder="0"
            />
          </Field>
          <Field label="Type" htmlFor="tache-type">
            <select id="tache-type" className="input" value={type} onChange={(event) => setType(event.target.value)}>
              {TASK_TYPES.map((value) => (
                <option key={value} value={value}>{formatLabel(value)}</option>
              ))}
            </select>
          </Field>
          <Field label="Priorité" htmlFor="tache-priorite">
            <select
              id="tache-priorite"
              className="input"
              value={priorite}
              onChange={(event) => setPriorite(event.target.value)}
            >
              {TASK_PRIORITIES.map((value) => (
                <option key={value} value={value}>{formatLabel(value)}</option>
              ))}
            </select>
          </Field>
        </Modal>
      )}
    </>
  )
}
