package com.ipcb.milleuro.model

class Question(val questionText: String,
    val answers: HashSet<Answer>,
    val correctAnswer: Answer) {

}