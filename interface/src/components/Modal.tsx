import { useEffect, type FormEvent, type ReactNode } from 'react'
import type { ApiError } from '../lib/apiClient'
import { Button } from './Button'

interface ModalProps {
  title: string
  description?: string
  submitLabel: string
  pending: boolean
  error: ApiError | null
  onClose: () => void
  onSubmit: (event: FormEvent<HTMLFormElement>) => void
  children: ReactNode
}

export function Modal({
  title,
  description,
  submitLabel,
  pending,
  error,
  onClose,
  onSubmit,
  children,
}: ModalProps) {
  useEffect(() => {
    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === 'Escape') onClose()
    }
    window.addEventListener('keydown', handleKeyDown)
    return () => window.removeEventListener('keydown', handleKeyDown)
  }, [onClose])

  return (
    <div className="modal-backdrop" onMouseDown={onClose}>
      <div
        className="modal"
        role="dialog"
        aria-modal="true"
        aria-label={title}
        onMouseDown={(event) => event.stopPropagation()}
      >
        <div className="modal__header">
          <h2>{title}</h2>
          <button type="button" className="icon-button" aria-label="Fermer" onClick={onClose}>
            ×
          </button>
        </div>
        {description && <p className="modal__description">{description}</p>}

        <form onSubmit={onSubmit}>
          <div className="modal__body">{children}</div>
          {error && <p className="form-error" role="alert">{error.message}</p>}
          <div className="modal__footer">
            <Button variant="subtle" onClick={onClose} disabled={pending}>
              Annuler
            </Button>
            <button type="submit" className="button button--primary" disabled={pending}>
              {pending ? 'Enregistrement…' : submitLabel}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
