import { useState, type FormEvent } from 'react'
import { Button } from '../components/Button'
import { EmptyBlock, ErrorBlock, LoadingBlock } from '../components/Feedback'
import { Field } from '../components/Field'
import { Modal } from '../components/Modal'
import { PageHeader } from '../components/PageHeader'
import { ProjectCard } from '../components/ProjectCard'
import { useCreateProject } from '../hooks/useProjectMutations'
import { useProjects } from '../hooks/useProjects'
import { PROJECT_STATUSES, formatLabel } from '../types/domain'

export function ProjectsPage() {
  const { projects, error, loading, reload } = useProjects()
  const { run: create, pending, error: createError } = useCreateProject()
  const [modalOpen, setModalOpen] = useState(false)
  const [nom, setNom] = useState('')
  const [lang, setLang] = useState('fr')
  const [statut, setStatut] = useState<string>('A_FAIRE')

  const closeModal = () => {
    setModalOpen(false)
    setNom('')
    setLang('fr')
    setStatut('A_FAIRE')
  }

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault()
    const created = await create({ nom, lang, statut })
    if (created) {
      closeModal()
      reload()
    }
  }

  return (
    <>
      <PageHeader
        title="Projets"
        description="Vue d'ensemble des projets et de leur avancement."
        actions={
          <Button onClick={() => setModalOpen(true)}>+ Nouveau projet</Button>
        }
      />

      {loading ? (
        <LoadingBlock />
      ) : error ? (
        <ErrorBlock error={error} onRetry={reload} />
      ) : projects.length === 0 ? (
        <EmptyBlock
          title="Aucun projet"
          description="Créez un premier projet pour commencer à suivre les tâches."
        />
      ) : (
        <div className="project-grid">
          {projects.map((project) => (
            <ProjectCard key={project.id} project={project} />
          ))}
        </div>
      )}

      {modalOpen && (
        <Modal
          title="Nouveau projet"
          description="Le projet est enregistré dans le service de gestion des projets."
          submitLabel="Créer le projet"
          pending={pending}
          error={createError}
          onClose={closeModal}
          onSubmit={handleSubmit}
        >
          <Field label="Nom du projet" htmlFor="projet-nom">
            <input
              id="projet-nom"
              className="input"
              value={nom}
              onChange={(event) => setNom(event.target.value)}
              placeholder="Ex. Migration API"
              required
              autoFocus
            />
          </Field>
          <Field label="Statut initial" htmlFor="projet-statut">
            <select
              id="projet-statut"
              className="input"
              value={statut}
              onChange={(event) => setStatut(event.target.value)}
            >
              {PROJECT_STATUSES.map((value) => (
                <option key={value} value={value}>{formatLabel(value)}</option>
              ))}
            </select>
          </Field>
          <Field label="Langue" htmlFor="projet-lang">
            <select
              id="projet-lang"
              className="input"
              value={lang}
              onChange={(event) => setLang(event.target.value)}
            >
              <option value="fr">Français</option>
              <option value="en">Anglais</option>
            </select>
          </Field>
        </Modal>
      )}
    </>
  )
}
