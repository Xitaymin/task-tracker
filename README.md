TASK TRACKER

Application for managing tasks between users that consists in teams and relates to projects.
Based on Spring Boot and Hibernate.

To run:

1. Start Postgresql container from root folder using `docker-compose up`<p>
   (Note: you can use Postgresql server installed on your machine but in this case
   user credentials should be the same as in application.properties, and access to DB should be provided,
   also Postgresql version should be below 15, (tested on 13.4))
2. Start the application using main method from TaskTrackerApplication.java

Implemented business operations on entities:
<ol>
<li>User</li>
<ol>
<li>CRUD for the user. User email must be unique.</li>
<li>User editing. You can edit everything except ID. Editing does not affect the list of commands</li>
<li>Deleting a user. When a user is deleted, it actually remains, but is marked as deleted=true. All operations involving the user in other entities are available only for deleted=false.</li>
<li>Getting a user by ID along with his tasks and team.</li>
<li>Get all users.</li>
</ol>
<li>Team</li>
<ol>
<li>CRUD for the team.</li>
<ol>
<li>A team can be created immediately with a list of users.</li>
<li>Only the name can be edited.</li>
<li>You can delete a team only if it has no projects and members</li>
</ol>
<li>Adding a user to a team, a user can only be on one team. The maximum command size is defined in properties. Users with MANAGER and ADMIN roles cannot be added to teams.</li>
<li>Assign a team leader. He must be part of the team at the time of appointment and have the role of LEAD.</li>
</ol>
<li>Project</li>
<ol>
<li>Project creation.</li>
<li>Only the name can be edited</li>
<li>You cannot delete a project.</li>
<li>Add/remove a team to a project.</li>
<li>Assign productOwner, it must have MANAGER role</li>
</ol>
<li>Task</li>
<ol>
<li>Create a task.</li>
<ol>
<li>A task can only be created within an existing project</li>
<li>Assignee - may not be specified when creating the task, but if specified, it must be in the group of the task associated with the project and must not be deleted</li>
<li>Title and description are required</li>
<li>reporter - any user</li>
</ol>
<li>Tasks cannot be deleted</li>
<li>Editing. Only title and description can be edited. project and reporter cannot be changed. Assignee is changed only by the corresponding operation</li>
<li>Binding a task to a user - set the assignee. Assignee must be in one of the groups associated with the project that this task belongs to. .</li>
<li>Creating child tasks:</li>
<ol>
<li>You cannot create tasks with the SUBTASK type without a parent task.</li>
<li>2 hierarchies EPIC -> STORY -> ISSUE -> SUBTASK and EPIC -> BUG</li>
<li>Each task type can only contain child tasks of the next level.</li>
</ol>
</ol>
</ol>

Road map (next steps in development):
<ol>
<li> Implement admin pages</li>
<ol>
<li>List of all users with the ability to edit and delete them</li>
<li>Add/Edit User Page</li>
<li>Creating/editing tasks</li>
<ol>
<li>project selection</li>
<li>assignee choice (The choice of users and projects on the UI is done through drop-down lists)</li>
<li>the reporter is set from the currently logged in user</li>
</ol>
<li>List page of all team tasks</li>
<ol>
<li>by default, we issue all tasks of all users and projects</li>
<li>it is possible to filter by</li>
<ol>
<li>user</li>
<li>project</li>
</ol>
</ol>
</ol>
<li> Enable security: security should work, not only on the admin page, but also in the rest of the implementation</li>
<ol>
<li>authentication - formlogin</li>
<li>authorization</li>
<ol>
<li>editing users - available only to users with the admin role</li>
<li>creating/editing tasks is available only if there is a write role</li>
<li>All logged in users have read access</li>
</ol>
</ol>
</ol>