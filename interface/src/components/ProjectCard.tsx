import { Link } from 'react-router-dom'
import { formatLabel, type Project } from '../types/domain'
import { Badge } from './Badge'

export function ProjectCard({ project }: { project: Project }) {
  const total = project.taches.length
  const done = project.taches.filter((task) => task.etat === 'TERMINE').length
  const progress = total === 0 ? 0 : Math.round((done / total) * 100)

  return (
    <Link to={`/projects/${project.id}`} className="project-card">
      <div className="project-card__top">
        <span className="project-card__key">{project.id}</span>
        <Badge value={project.statut} />
      </div>
      <h3>{project.nom}</h3>
      <div className="progress" aria-label={`Avancement ${progress} %`}>
        <div className="progress__bar" style={{ width: `${progress}%` }} />
      </div>
      <div className="project-card__meta">
        <span>{done}/{total} tâches terminées</span>
        <span>{project.developpeurs.length} membre{project.developpeurs.length > 1 ? 's' : ''}</span>
      </div>
      <div className="project-card__footer">
        <span>{formatLabel(project.lang)}</span>
        <span className="project-card__open">Ouvrir →</span>
      </div>
    </Link>
  )
}
