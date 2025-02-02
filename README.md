# IDDE laborfeladatok

# Webserver
gradle undeploy
gradle deploy
& "$env:CATALINA_HOME\bin\startup.bat"
& "$env:CATALINA_HOME\bin\shutdown.bat"

# TODO Samples
{
"title": "Complete Spring Project",
"description": "Finish the implementation and testing of the Spring project.",
"date": "2024-12-15T10:00:00",
"priority": 2
}

{
"title": "Buy Groceries",
"description": "Get milk, eggs, and bread from the store.",
"date": "2024-02-10T15:30:00",
"priority": 1
}

{
"title": "Prepare Presentation",
"description": "Create slides and practice for the Monday meeting.",
"date": "2024-02-12T09:00:00",
"priority": 3
}

{
"title": "Gym Workout",
"description": "Follow a full-body strength training routine.",
"date": "2024-02-11T18:00:00",
"priority": 2
}

{
"title": "Read a Book",
"description": "Finish reading 'Clean Code' by Robert C. Martin.",
"date": "2024-02-15T20:00:00",
"priority": 1
}

# TASK Sample

{
"description":"Hello"
}


{
"description": "Research topic for presentation"
}

{
"description": "Write first draft of report"
}

{
"description": "Review and edit document"
}

{
"description": "Submit final version"
}

# TODO spring url
http://localhost:8081/api/todos

# TODO web url
http://localhost:8080/ToDoList-pgim2289/todos

# TODO task url
http://localhost:8081/api/todos/1/tasks

# TODO query paramter url
http://localhost:8080/ToDoList-pgim2289/todos?id=2