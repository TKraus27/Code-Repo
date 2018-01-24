var didScroll;
var lastScrollTop = 0;
var delta = 5;
var navbarHeight = $('header').outerHeight();

$(window).scroll(
  function(){
    didScroll = true;
    var yPos = ($(window).scrollTop()/3);
    $('div.slide').css('background-position-y', yPos+'px');
  }
);

setInterval(function() {
  if (didScroll) {
    hasScrolled();
    didScroll = false;
  }
}, 250);

function hasScrolled() {
  var st = $(this).scrollTop();

  if (st > $(window).height()-70) {
    $('header.header-down').addClass('background');
  } else {
    $('header.header-down').removeClass('background');
  }
  // Make sure they scroll more than delta
  if(Math.abs(lastScrollTop - st) <= delta)
    return;

  if (st > lastScrollTop && st > navbarHeight){
    $('header').removeClass('header-down').addClass('header-up');
  } else if (st + $(window).height() < $(document).height()) {
    $('header').removeClass('header-up').addClass('header-down');
  }

  lastScrollTop = st;
}
