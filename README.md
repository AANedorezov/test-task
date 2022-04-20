# test-task
Test task for Renue

1) *path_to_test-task*: mvn clean package
2) java -Xmx9m -jar target/TestTask-1.0-SNAPSHOT-jar-with-dependencies.jar *column_number* *-tree*/*-binary*

*-tree*/*-binary* -> second argument can be -tree or -binary. If -tree choosed than AVLTree will be used to filter records, else Binary search will be used.