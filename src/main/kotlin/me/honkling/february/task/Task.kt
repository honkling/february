package me.honkling.february.task

abstract class Task(val repeatEvery: Int) {
    abstract fun execute()
}