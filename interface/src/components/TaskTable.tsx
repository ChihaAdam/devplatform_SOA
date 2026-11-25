import { Badge } from './Badge'
import { formatLabel, TASK_STATES, type Task } from '../types/domain'

interface TaskTableProps {
  tasks: Task[]
  onStateChange: (task: Task, etat: string) => void
  disabled?: boolean
}

export function TaskTable({ tasks, onStateChange, disabled = false }: TaskTableProps) {
  if (tasks.length === 0) {
    return <p className="section-empty">Aucune tâche pour ce projet.</p>
  }

  return (
    <div className="table-scroll">
      <table className="task-table">
        <thead>
          <tr>
            <th>Tâche</th>
            <th>Type</th>
            <th>Priorité</th>
            <th>Temps estimé</th>
            <th>État</th>
          </tr>
        </thead>
        <tbody>
          {tasks.map((task) => (
            <tr key={task.id}>
              <td>
                <span className="task-table__key">{task.id}</span>
                <span className="task-table__title">{task.titre}</span>
              </td>
              <td><Badge value={task.type} /></td>
              <td><Badge value={task.priorite} /></td>
              <td>{task.tempsEstime} h</td>
              <td>
                <select
                  className="state-select"
                  aria-label={`État de la tâche ${task.titre}`}
                  value={task.etat}
                  disabled={disabled}
                  onChange={(event) => onStateChange(task, event.target.value)}
                >
                  {TASK_STATES.map((state) => (
                    <option key={state} value={state}>{formatLabel(state)}</option>
                  ))}
                  {!TASK_STATES.includes(task.etat as (typeof TASK_STATES)[number]) && (
                    <option value={task.etat}>{formatLabel(task.etat)}</option>
                  )}
                </select>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
