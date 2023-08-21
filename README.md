# APIHousing

A tool meant to help Kitchener-Waterloo students find housing and roommates much easier.


## Run the project
Type this in your terminal

mvn spring-boot:run

## To findout port number
Type this in your terminal
lsof -i :8080; kill -9 <PID>


## start a new tmux session
tmux new -s <session name>

## attach to a tmux session
tmux a -t <session name>

## detach from a tmux session
ctrl+b d

## list all tmux sessions
tmux ls

## kill a tmux session
tmux kill-session -t <session name>

## kill all tmux sessions
tmux kill-server
