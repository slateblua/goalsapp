package com.bluesourceplus.bluedays.data.database

import com.bluesourceplus.bluedays.data.GoalModel

fun GoalModel.toGoalEnt(): GoalEnt {
    return GoalEnt(
        id = this.id,
        title = this.title,
        description = this.description,
    )
}

fun GoalEnt.toGoalModel(): GoalModel {
    return GoalModel(
        id = this.id,
        title = this.title,
        description = this.description,
    )
}