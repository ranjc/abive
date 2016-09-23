if(window.chrome) {
    $('.banner li').css('background-size', '100% 100%');
}

$('.banner').unslider({
    arrows: false,
    fluid: true,
    dots: true
});

//  Find any element starting with a # in the URL
//  And listen to any click events it fires
$('a[href^="#"]').click(function() {
    //  Find the target element
    var target = $($(this).attr('href'));

    //  And get its position
    var pos = target.offset(); // fallback to scrolling to top || {left: 0, top: 0};

    //  jQuery will return false if there's no element
    //  and your code will throw errors if it tries to do .offset().left;
    if(pos) {
        //  Scroll the page
        $('html, body').animate({
            scrollTop: pos.top,
            scrollLeft: pos.left
        }, 1000);
    }

    //  Don't let them visit the url, we'll scroll you there
    return false;
});
