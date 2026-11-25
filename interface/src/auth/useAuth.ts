import { useContext } from 'react'
import { AuthContext, type AuthValue } from './authContext'

export function useAuth(): AuthValue {
  const value = useContext(AuthContext)
  if (!value) {
    throw new Error('useAuth doit etre utilise dans AuthProvider')
  }
  return value
}
