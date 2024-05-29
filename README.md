# Ticket System Doc

### Structure
- Ticket system consists of TicketController and two services implementing TicketService
    - [TicketController](src/main/java/cz/ticketsystem/controller/TicketController.java) contains 5 endpoints
        - /all - gets all Tickets and returns them as a List
        - /create - creates a new ticket and saves it to memory
        - /first - return the value of the first ticket in memory
        - /deleteFirst - return and delete the first ticket
        - /deleteLast - return and delete the last ticket
    - [TicketQueueService](src/main/java/cz/ticketsystem/service/TicketQueueService.java)
        - set to PriorityQueue
        - during the implementation I realized that I can't easily get all the elements without removing them from PriorityQueue
        - replaced by TicketListService
    - [TicketListService](src/main/java/cz/ticketsystem/service/TicketListService.java)
        - built on LinkedList, which can perform all the necessary operations from the input
        - for efficiency, the order is not stored in memory, but created on request
    - [Ticket](src/main/java/cz/ticketsystem/model/Ticket.java)
        - model object
        - keeps id and creationDateTime
        - can be used to create a TicketWithOrder
    - [TicketWithOrder](src/main/java/cz/ticketsystem/model/TicketWithOrder.java)
        - Ticket descendant
        - maintains an additional order
    - [TicketSystemApplicationTests](src/test/java/cz/ticketsystem/TicketSystemApplicationTests.java)
        - contains a couple of simple tests for TicketService
        - TicketService is chosen according to @Primary's annotation in one of the implementations
- The project also contains a simple web interface
    - the interface is accessible from [localhost:8080](http://localhost:8080/) in the default configuration
    - when implementing this part, there was not so much attention to detail, because this part was not related to the input

### Time required
- The whole project took me about 5 hours to build
    - The structure and main logic of the REST API took me 1.5h
        - I got stuck on an inappropriate implementation using PriorityQueue
        - The time was also a bit prolonged by the ambiguity of the assignment, when I was not sure if the first element in the queue should be removed.
    - The test implementation took about 30 min
    - The rest of the time I spent creating an html template together with JS logic for getting data from the API
        - The [index.html](src/main/resources/static/index.html) is created on [Bootstrap 5.3](https://getbootstrap.com/docs/5.3/) using the [Cover](https://getbootstrap.com/docs/5.3/examples/cover/) template
            - icon from [FontAwesome](https://fontawesome.com/)
        - [main.js](src/main/resources/static/main.js) (needs a few more modifications to be considered clean-code)
            - written in vanilla JS
### Possible extensions
### Možná rozšíření
- Instead of using non-persistent memory, Tickets can be stored in the database. (e.g. mybatis for this type of task)

