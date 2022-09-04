(function ($) {
  $.fn.extend({
    ledTime: function (method) {

      return this.each(function () {
        var digitSegments = [
          [1, 2, 3, 4, 5, 6],
          [2, 3],
          [1, 2, 7, 5, 4],
          [1, 2, 7, 3, 4],
          [6, 7, 2, 3],
          [1, 6, 7, 3, 4],
          [1, 6, 5, 4, 3, 7],
          [1, 2, 3],
          [1, 2, 3, 4, 5, 6, 7],
          [1, 2, 7, 3, 6]
        ]

        var html = '<div class="digit hours"><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div></div><div class="digit hours"><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div></div><div class="separator"></div><div class="digit minutes"><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div></div><div class="digit minutes"><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div></div><div class="separator"></div><div class="digit seconds"><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div></div><div class="digit seconds"><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div><div class="segment"></div></div>';
        $(this).append(html);

        var _hours = this.querySelectorAll('.hours');
        var _minutes = this.querySelectorAll('.minutes');
        var _seconds = this.querySelectorAll('.seconds');

        setInterval(function () {
          var date = new Date();
          var hours = date.getHours(),
            minutes = date.getMinutes(),
            seconds = date.getSeconds();

          setNumber(_hours[0], Math.floor(hours / 10), 1);
          setNumber(_hours[1], hours % 10, 1);

          setNumber(_minutes[0], Math.floor(minutes / 10), 1);
          setNumber(_minutes[1], minutes % 10, 1);

          setNumber(_seconds[0], Math.floor(seconds / 10), 1);
          setNumber(_seconds[1], seconds % 10, 1);
        }, 1000);

        var setNumber = function (digit, number, on) {
          var segments = digit.querySelectorAll('.segment');
          var current = parseInt(digit.getAttribute('data-value'));

          // only switch if number has changed or wasn't set
          if (!isNaN(current) && current != number) {
            // unset previous number
            digitSegments[current].forEach(function (digitSegment, index) {
              setTimeout(function () {
                segments[digitSegment - 1].classList.remove('on');
              }, index * 45)
            });
          }

          if (isNaN(current) || current != number) {
            // set new number after
            setTimeout(function () {
              digitSegments[number].forEach(function (digitSegment, index) {
                setTimeout(function () {
                  segments[digitSegment - 1].classList.add('on');
                }, index * 45)
              });
            }, 250);
            digit.setAttribute('data-value', number);
          }
        }
      });
    }
  })
})(jQuery)