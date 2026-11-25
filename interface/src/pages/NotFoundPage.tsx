import { Link } from 'react-router-dom'
import { PageHeader } from '../components/PageHeader'

export function NotFoundPage() {
  return (
    <>
      <PageHeader title="Page introuvable" description="Cette adresse ne correspond à aucune page." />
      <div className="panel placeholder-panel">
        <Link className="text-link" to="/projects">Retour aux projets</Link>
      </div>
    </>
  )
}
