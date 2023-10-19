package com.example.myapplication

object Constants {
    val USER_NAME: String = "user_name"
    val TOTAL_QUESTIONS: String = "total_questions"
    val SCORE: String = "score"

    fun getQuestions(): ArrayList<DataQuestionsClass> {
        val questionsList = ArrayList<DataQuestionsClass>()

        // 1
        val questionOne = DataQuestionsClass(
            1,
            "What country does this flag belongs to?",
            R.drawable.ic_flag_of_argentina,
            arrayListOf("Argentina", "Australia", "Armenia", "Austria"),
            0,
        )
        questionsList.add(questionOne)

        // 2
        val questionTwo = DataQuestionsClass(
            2,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_australia,
            arrayListOf("Angola", "Austria",
                "Australia", "Armenia"),
            2
        )
        questionsList.add(questionTwo)

        // 3
        val questionThree = DataQuestionsClass(
            3,
            "What country does this flag belong to?",
            R.drawable.ic_flag_of_brazil,
            arrayListOf("Belarus", "Belize",
                "Brunei", "Brazil"),
            3
        )
        questionsList.add(questionThree)

        return questionsList
    }
}