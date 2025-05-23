import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.RunGoalClass
import com.example.myapplication1.WalkGoalClass

class WalkAdapter (
    private val goals: List<WalkGoalClass>,
): RecyclerView.Adapter<WalkAdapter.GoalsViewHolder>() {

    class GoalsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeGoal: TextView = itemView.findViewById(R.id.timeGoal)
        val kmGoal: TextView = itemView.findViewById(R.id.kmGoal)
    }
    //ניפוח קובייה אל תוך parent(MyFriends)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.walk_item, parent, false)
        return GoalsViewHolder(view) }

    override fun onBindViewHolder(holder: GoalsViewHolder, position: Int) {
        val goal = goals[position]
        holder.timeGoal.text = "Time: ${goal.time}"
        holder.kmGoal.text = "Km: ${goal.km}"
    }


    override fun getItemCount(): Int {
        return goals.size    }

}