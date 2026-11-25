import { useCallback, useState } from 'react'
import { toApiError, type ApiError } from '../lib/apiClient'

/** Hook d'ecriture : `run` renvoie null en cas d'echec et expose l'erreur a afficher. */
export function useMutation<TArgs extends unknown[], TResult>(
  mutation: (...args: TArgs) => Promise<TResult>,
) {
  const [pending, setPending] = useState(false)
  const [error, setError] = useState<ApiError | null>(null)

  const run = useCallback(
    async (...args: TArgs): Promise<TResult | null> => {
      setPending(true)
      setError(null)
      try {
        return await mutation(...args)
      } catch (caught) {
        setError(toApiError(caught))
        return null
      } finally {
        setPending(false)
      }
    },
    [mutation],
  )

  return { run, pending, error }
}
