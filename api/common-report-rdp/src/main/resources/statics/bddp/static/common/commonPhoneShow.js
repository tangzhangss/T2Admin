window.onload = function () {
    setTimeout(function () {
        $(".bg").fadeOut(function () {
            initCreateTags();
        });
    }, 1000);
};