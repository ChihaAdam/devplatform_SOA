import { useAuth } from '../auth/useAuth'

export function Topbar() {
  const { isAuthenticated } = useAuth()

  return (
    <header className="topbar">
      <div className="topbar__context">
        <span className="topbar__product">DevPlatform</span>
        <span className="topbar__separator">/</span>
        <span>Espace projets</span>
      </div>
      <div className="topbar__user">
        <span className="topbar__mode">{isAuthenticated ? 'Session active' : 'Mode démo'}</span>
        <span className="avatar avatar--small" aria-hidden="true">?</span>
      </div>
    </header>
  )
}
