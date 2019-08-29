let express = require('express');
let app = express();
let server = require('http').Server(app);
let io = require('socket.io')(server);
let port = 3000;
let rooms = new Set();
let messages = [];
app.use(express.static('public'));

server.listen(port, () => {
    console.log(`Rodando na porta ${port}`);
});

app.get("/", (req, res) => {
    res.sendFile(__dirname, "/public/index.html")
});

io.on("connection", (socket) => {
    socket.on("create-chat", (data) => {
        let room = rooms[data];

        if (room == null) {
            messages[data] = [];
        }

        rooms.add(data);
        socket.join(data);
        io.sockets.emit("result_chat", Array.from(rooms));
    });

    socket.on("join-chat", (data) => {
        socket.join(data);
        io.sockets.in(data).emit("messages-chat", Array.from(messages[data]));
    });

    socket.on("leave-chat", (data) => {
        // socket.leave(data);
        socket.leave(data);
        io.in(data).clients((err, clients) => {
            if (clients.length < 1) {
                rooms.delete(data);
                io.sockets.emit("result_chat", Array.from(rooms));
            }
        });
    });

    socket.on("send-message-chat", (data) => {
        let json = JSON.parse(data);
        messages[json["room"]].push(json["message"]);
        io.sockets.in(json["room"]).emit("receive-message", json["message"]);
    });

    socket.emit("result_chat", Array.from(rooms));
});