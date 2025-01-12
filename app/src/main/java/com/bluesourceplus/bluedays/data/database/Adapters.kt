package com.bluesourceplus.bluedays.data.database

import com.bluesourceplus.bluedays.data.GoalModel

fun GoalModel.toGoalEnt(): GoalEnt {
    return GoalEnt(
        goalId = this.goalId,
        title = this.title,
        description = this.description,
        dueDate = this.dueDate,
        completed = this.completed,
    )
}

fun GoalEnt.toGoalModel(): GoalModel {
    return GoalModel(
        goalId = this.goalId,
        title = this.title,
        description = this.description,
        dueDate = this.dueDate,
        completed = this.completed,
    )
}