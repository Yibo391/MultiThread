# MultiThread

1. Implement a program in Java that satisfies functional requirements listed below (also see the additional requirements for your code below this list):
   - The execution of the tasks proceeds for the specified total time (in milliseconds); note that *real (clock) time* is used for this task, in contrast to Individual Assignment 2, which relied on loop iterations as abstract time units / CPU ticks.
   - The total number of tasks is limited to a specific number. As the tasks are created, they must be provided with sequential IDs (0, 1, 2, ...)
   - The total burst time (duration) for each task is determined randomly (using a uniform distribution) between the given minimum and maximum burst time.
   - The task to be assigned to a thread for execution is selected sequentially (first, task with ID 0, then task with ID 1, etc.).
   - The total number of threads is specified by the value given in the source code.
   - As the task is being executed by a thread, it is not expected to do any specific work (calculations, etc.), but rather simply go to sleep for X milliseconds (value specified in the source code) repeatedly until its complete burst time (duration) passes. Then it is considered to be complete.
   - When the simulation time is up, it should make sure to stop all of the currently executing and waiting threads and tasks!
   - After that, it should print the results in the way specified in the source code file (details about the completed tasks, and lists of IDs for the incomplete/interrupted tasks as well as waiting/non-scheduled tasks).
   - The implementation should run several simulations (the number of simulations is specified in the *main()* function in the provided source code file). Afterwards, it must stop/terminate cleanly (i.e., it should not remaining running due to incomplete threads...)