import { useState, type ReactNode } from 'react'
import { AuthContext, type AuthValue } from './authContext'
import { getAccessToken, setAccessToken } from './session'

export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState<string | null>(() => getAccessToken())

  const value: AuthValue = {
    isAuthenticated: token !== null,
    user: null,
    setSession: (nextToken) => {
      setAccessToken(nextToken)
      setToken(nextToken)
    },
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}
