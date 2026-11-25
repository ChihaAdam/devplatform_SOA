import { formatLabel, type Developer } from '../types/domain'

function initials(name: string): string {
  return name
    .split(' ')
    .filter(Boolean)
    .slice(0, 2)
    .map((part) => part[0]?.toUpperCase() ?? '')
    .join('')
}

export function TeamList({ developers }: { developers: Developer[] }) {
  if (developers.length === 0) {
    return <p className="section-empty">Aucun membre renseigné.</p>
  }

  return (
    <ul className="team-list">
      {developers.map((developer) => (
        <li key={developer.id} className="team-list__item">
          <span className="avatar" aria-hidden="true">{initials(developer.nom)}</span>
          <span className="team-list__identity">
            <strong>{developer.nom}</strong>
            <small>{developer.email}</small>
          </span>
          <BadgeRole role={developer.role} />
        </li>
      ))}
    </ul>
  )
}

function BadgeRole({ role }: { role: string }) {
  return <span className="role-tag">{formatLabel(role)}</span>
}
