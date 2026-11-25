import { createContext } from 'react'

export interface AuthValue {
  isAuthenticated: boolean
  /** Remplace par l'utilisateur renvoye par le futur service d'authentification. */
  user: null
  setSession: (token: string | null) => void
}

export const AuthContext = createContext<AuthValue | null>(null)
