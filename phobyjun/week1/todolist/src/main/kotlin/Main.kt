import todolist.LoginFailedException
import todolist.TaskList
import todolist.User
import todolist.UserSession

fun main() {
    var currentUser: User? = null
    var currentTaskList: TaskList? = null

    while (true) {
        // user authentication
        while (currentUser == null) {
            println("1. 로그인\t2. 유저 등록")
            val inputCommand = input().toInt()

            val userName = input("이름: ")
            val password = input("비밀번호: ")
            try {
                when (inputCommand) {
                    1 -> {
                        UserSession.loginUser(userName, password)
                        currentUser = UserSession.currentUser
                    }
                    2 -> {
                        UserSession.newUser(userName, password)
                    }
                }
            } catch (e: LoginFailedException) {
                println(e.message)
            }
        }

        // todolist application
        while (currentUser != null) {
            while (currentTaskList == null) {
                currentUser?.printCurrentTaskList()

                print("TodoList를 선택해주세요.\nTodoList 목록 | ")
                currentUser!!.printTaskLists()

                println("1. TodoList 생성\t2. TodoList 선택\t3. 로그아웃")
                when (input().toInt()) {
                    1 -> currentUser.newTaskList(input("TodoList 이름: "))
                    2 -> currentTaskList = currentUser
                        .selectTaskList(input("선택할 TodoList 이름: "))
                    3 -> {
                        currentUser = null
                        currentTaskList = null
                    }
                }
            }
            while (currentTaskList != null) {
                currentUser?.printCurrentTaskList()

                currentTaskList.printTasks()
                println("1. Task 생성\t2. 일정 Task 생성\t3. TodoList 변경")
                when (input().toInt()) {
                    1 -> currentTaskList.newTask(input("할 일: "))
                    2 -> currentTaskList.newTaskWithDueDate(input("할 일: "), input("기한: "))
                    3 -> currentTaskList = null
                }
            }
        }
    }
}

fun input(message: String = ""): String {
    print("> $message")
    return readLine()!!
}