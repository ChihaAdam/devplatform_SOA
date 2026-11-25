import { NavLink } from 'react-router-dom'

const navItems = [
  {
    to: '/projects',
    label: 'Projets',
    icon: (
      <path d="M3 4h7v7H3zM14 4h7v4h-7zM14 12h7v8h-7zM3 15h7v5H3z" />
    ),
  },
  {
    to: '/equipe',
    label: 'Équipe',
    icon: <path d="M12 12a4 4 0 1 0 0-8 4 4 0 0 0 0 8Zm-8 8a8 8 0 0 1 16 0" />,
  },
  {
    to: '/rapports',
    label: 'Rapports',
    icon: <path d="M4 20V10m6 10V4m6 16v-7m4 7H2" />,
  },
]

export function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="sidebar__brand">
        <span className="brand-mark" aria-hidden="true">D</span>
        <span>DevPlatform</span>
      </div>

      <nav className="sidebar__nav" aria-label="Navigation principale">
        <p className="sidebar__caption">Espace de travail</p>
        {navItems.map((item) => (
          <NavLink
            key={item.to}
            to={item.to}
            className={({ isActive }) => `sidebar__link${isActive ? ' is-active' : ''}`}
          >
            <svg viewBox="0 0 24 24" aria-hidden="true" fill="none" stroke="currentColor" strokeWidth="1.8">
              {item.icon}
            </svg>
            <span>{item.label}</span>
          </NavLink>
        ))}
      </nav>

      <div className="sidebar__footer">Projet académique · Architecture SOA</div>
    </aside>
  )
}
