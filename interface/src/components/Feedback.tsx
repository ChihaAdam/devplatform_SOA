import type { ApiError } from '../lib/apiClient'
import { Button } from './Button'

export function LoadingBlock({ label = 'Chargement…' }: { label?: string }) {
  return (
    <div className="feedback" role="status">
      <span className="feedback__spinner" aria-hidden="true" />
      <span>{label}</span>
    </div>
  )
}

export function ErrorBlock({ error, onRetry }: { error: ApiError; onRetry?: () => void }) {
  return (
    <div className="feedback feedback--error" role="alert">
      <div>
        <strong>Impossible de charger les données</strong>
        <p>{error.message}</p>
      </div>
      {onRetry && (
        <Button variant="subtle" onClick={onRetry}>
          Réessayer
        </Button>
      )}
    </div>
  )
}

export function EmptyBlock({ title, description }: { title: string; description: string }) {
  return (
    <div className="feedback feedback--empty">
      <strong>{title}</strong>
      <p>{description}</p>
    </div>
  )
}
