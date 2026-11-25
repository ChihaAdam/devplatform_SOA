import { formatLabel } from '../types/domain'

const TONES: Record<string, string> = {
  A_FAIRE: 'neutral',
  EN_COURS: 'blue',
  REVUE: 'purple',
  TERMINE: 'green',
  HAUTE: 'red',
  MOYENNE: 'yellow',
  BASSE: 'neutral',
  BUG: 'red',
  FONCTIONNALITE: 'blue',
  TACHE: 'neutral',
}

export function Badge({ value }: { value: string }) {
  const tone = TONES[value] ?? 'neutral'
  return <span className={`badge badge--${tone}`}>{formatLabel(value)}</span>
}
