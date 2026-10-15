setInterval(showTime, 1000);

function showTime() {
    let time = new Date();

    let hour = time.getHours();
    let min = time.getMinutes();
    let sec = time.getSeconds();

    hour = hour < 10 ? "0" + hour : hour;
    min = min < 10 ? "0" + min : min;
    sec = sec < 10 ? "0" + sec : sec;

    let currentTime = hour + ":" + min + ":" + sec;

    // find every .clock on the page and put the time in each one
    let clocks = document.querySelectorAll(".clock");

    for (let i = 0; i < clocks.length; i++) {
        clocks[i].innerHTML = currentTime;
    }}
showTime();