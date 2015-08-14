function setTab(n, min, count) {
    for (i = min; i < count; i++) {
        if (i == n) {
            document.getElementById("main" + i).style.display = "";
            document.getElementById("tab" + i).style.backgroundImage = "url(../skin/images/wsyyt/btn_tab_hover.png)";
            document.getElementById("tab" + i).style.color = "#e9334c";
            document.getElementById("tab" + i).style.fontWeight = "bold";
            document.getElementById("tab" + i).style.borderWidth = "0px";
        }
        else {
            document.getElementById("main" + i).style.display = "none";
            document.getElementById("tab" + i).style.backgroundImage = "url(../skin/images/wsyyt/btn_tab_default.png)";
            document.getElementById("tab" + i).style.color = "#000000";
            document.getElementById("tab" + i).style.fontWeight = "normal";
            document.getElementById("tab" + i).style.borderWidth = "1px";
        }
    }
}