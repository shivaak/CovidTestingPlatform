Covid Testing Platform

Bengaluru is deploying a covid testing platform. Testing Centres(tc) are spread across the city whose (x,y) coordinates are available. Every testing centre has some testing kits, each testing kit being able to test one person for covid-19. There are three user-facing APIs(can be modelled as functions) exposed by the platform:

init(tc[]): This method is only called once and when the platform starts. The platform is provided with a list of testing centres. Each testing centre has a unique id, location and number of testing kits available.

book_test(user_coords): This is exposed to residents of the city. Each user indicates his/her current location(user_coords) and the platform tries to reserve a test kit for the person in any of the three nearest testing centres depending on where testing kits are available. In this situation, the method returns the testing centre id where the user's test kit was reserved. If there are no testing kits available in any of the testing centres, it puts the user in waitlist for every nearby centre.

receive_kit(tc_id): As and when testing kits become available, they are dispatched by the health department to one of the testing centres (distribution logic is a blackbox). When a testing centre receives a kit, it indicates the same using this method. This method should update the count of testing kits available at the testing centre if there is no waiting list, otherwise reserve a test kit for the first person in the waiting list. This method will return user details if some user in the waitlist was successfully reserved, null otherwise.

Functional Notes
System should be in a consistent state with respect to book_test and receive_kit calls. If there is a test kit that can be reserved for a user, it should be reserved rather than spare test kits remaining in the testing centre while there is a waiting list present. receive_kit and book_test should ensure: tc.waitlist.length >=0, iff tc.kits = 0
System should do fair test kit reservations. Users should be treated on FCFS basis from the viewpoint of a testing centre where this user is interested in.
There is no global waitlist. Each centre has its own waitlist constituting its nearby users. A user will be part of 3 waitlists simultaneously. As and when a user gets a test kit assigned in one of the centres, he should be removed from the waitlist of all the other centres as well.
There should be no double booking of a test kit in any given situation. There should not be multiple bookings of test kits for one user in any given situation.


Evaluation Notes
A test harness is a MUST which would concurrently do test bookings for multiple users as well as dispatch of kits across multiple test centres. The harness should test for correctness of the code when under stress.
Functional Notes above will be used to evaluate the candidate's solution.
Compiling and executing code during evaluation is a MUST.
Best practices of good OO design should be followed.
Coding round will be 120 minutes.
Bonus 1: Design to support N nearby centres instead of hard-coded "3".
Bonus 2: Return waitlist number(s) to user from book_test if test kit not available immediately.
Bonus 3: This platform runs on a single instance subject to power failures. All the state should be disk-backed for brownie points. In this case, init method will be modified to include the waitlist of testing centres as well (state read from disk).


Suggestions
You can assume that there are at least 3 testing centres in the city.
The candidate can choose to model the city on a linear scale instead of a 2D coordinate space.
The candidate can choose to bound the x,y coordinate space of the city to some sane positive range like (0,0) to (100,100).
Any unique id generation scheme can be used to uniquely identify users or testing centres. For example: Auto-increment, UUID. No other metadata like name/description is required to be captured for a user or a testing centre.
Platform need not maintain state for any user who already has a test kit successfully assigned. Only the current state of kits in a testing centre and the waiting list need to be maintained.
Distance between centres and users is the euclidean distance between their 2D coordinates: sqrt(pow(x2-x1, 2) + pow(y2-y1, 2))

