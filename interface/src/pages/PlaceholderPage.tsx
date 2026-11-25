import { PageHeader } from '../components/PageHeader'

interface PlaceholderPageProps {
  title: string
  description: string
}

export function PlaceholderPage({ title, description }: PlaceholderPageProps) {
  return (
    <>
      <PageHeader title={title} description="Espace préparé pour les prochaines étapes." />
      <div className="panel placeholder-panel">
        <p>{description}</p>
      </div>
    </>
  )
}
