package com.example.todoapp1.ui.home.fragments.tasks_list

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.domain.model.Task
import com.example.todoapp1.R
import com.example.todoapp1.databinding.ItemViewBinding
import java.text.SimpleDateFormat
import java.util.Date

class TasksAdapter(private var tasks: MutableList<Task>?) : Adapter<TasksAdapter.ViewHolder>() {
    class ViewHolder(val itemBinding: ItemViewBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bindData(task: Task) {
            val convertedTime = convertLongToTime(task.time!!)
            itemBinding.titleItem.text = task.title
            itemBinding.descriptionItem.text = convertedTime
        }

        @SuppressLint("SimpleDateFormat")
        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("hh:mm a")
            return format.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks?.size ?: 1
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(tasks!![position])
        holder.itemBinding.rightView.setOnClickListener {
            holder.itemBinding.swipeLayout.close(true)
            onItemDeletedListener?.onItemClick(position, tasks!![position])
        }
        holder.itemBinding.dragItem.setOnClickListener {
            onItemClickListener?.onItemClick(position, tasks!![position])
        }
        if (tasks!![position].isDone) {
            holder.itemBinding.rightImg.text = "Done!"
            holder.itemBinding.rightImg.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.transparent
                )
            )
            holder.itemBinding.rightImg.setTextColor(Color.GREEN)
            holder.itemBinding.rightImg.setIconTintResource(R.color.transparent)
            holder.itemBinding.titleItem.setTextColor(Color.GREEN)
            holder.itemBinding.view.setBackgroundColor(Color.GREEN)
        } else {
            holder.itemBinding.rightImg.text = ""
            holder.itemBinding.rightImg.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorPrimary
                )
            )
            holder.itemBinding.titleItem.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorPrimary
                )
            )
            holder.itemBinding.rightImg.setIconTintResource(R.color.white)
            holder.itemBinding.view.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorPrimary
                )
            )
        }
        holder.itemBinding.rightImg.setOnClickListener {
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

    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    fun updateTaskDeleted(task: Task, position: Int) {
        tasks?.remove(task)
        notifyDataSetChanged()
    }

    var onTodoStateChangedListener: OnItemClickListener? = null
    var onItemClickListener: OnItemClickListener? = null
    var onItemDeletedListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int, task: Task)
    }
}