package com.example.todoapp1.ui.home.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.todoapp1.DataBase.Task
import com.example.todoapp1.R
import com.google.android.material.button.MaterialButton
import com.zerobranch.layout.SwipeLayout
import java.text.SimpleDateFormat
import java.util.Date

class TasksAdapter(private var tasks: MutableList<Task>?) : Adapter<TasksAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title_item)
        var description: TextView = itemView.findViewById(R.id.description_item)
        var imgDelete: ImageView = itemView.findViewById(R.id.right_view)
        var swipe: SwipeLayout = itemView.findViewById(R.id.swipe_layout)
        var cardView: CardView = itemView.findViewById(R.id.drag_item)
        var rightClick: MaterialButton = itemView.findViewById(R.id.right_img)
        var view: View = itemView.findViewById(R.id.view)
        fun bind(task: Task) {
            val convertedTime = convertLongToTime(task.time!!)
            title.text = task.title
            description.text = convertedTime
        }

        @SuppressLint("SimpleDateFormat")
        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("dd-MM-yyyy")
            return format.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks?.size ?: 1
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks!![position])
        holder.imgDelete.setOnClickListener {
            holder.swipe.close(true)
            onItemClickListener?.onItemClick(position, tasks!![position])
        }
        holder.cardView.setOnClickListener {
            onItemClickListener2?.onItemClick(position, tasks!![position])
        }
        if (tasks!![position].isDone) {
            holder.rightClick.text = "Done!"
            holder.rightClick.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.transparent
                )
            )
            holder.rightClick.setTextColor(Color.GREEN)
            holder.rightClick.setIconTintResource(R.color.transparent)
            holder.title.setTextColor(Color.GREEN)
            holder.view.setBackgroundColor(Color.GREEN)
        } else {
            holder.rightClick.text = ""
            holder.rightClick.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorPrimary
                )
            )
            holder.title.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorPrimary
                )
            )
            holder.rightClick.setIconTintResource(R.color.white)
            holder.view.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorPrimary
                )
            )
        }


        holder.rightClick.setOnClickListener {
            tasks!![position].isDone = !(tasks!![position].isDone)
            notifyItemChanged(position)
            onTodoStateChangedListener?.onItemClick(position, tasks!![position])
        }
    }

    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    fun bindTasks(tasks: List<Task>) {
        this.tasks = tasks.toMutableList()
        notifyDataSetChanged()
    }

    fun updateTaskDeleted(task: Task) {
        tasks?.remove(task)
        notifyDataSetChanged()
    }

    var onTodoStateChangedListener: OnItemClickListener? = null
    var onItemClickListener2: OnItemClickListener? = null
    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int, task: Task)
    }
}