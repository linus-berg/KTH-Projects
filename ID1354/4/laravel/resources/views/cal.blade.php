@extends('master')
@section('header')
<link rel="stylesheet" href="{{ asset('css/cal.css') }}"> 
@endsection
@section('content')
<div id="calendar">
</div>
<script>
  /* Set navigation active. */
  //$('#home_nav').addClass('active_nav'); 
  $('#calendar_nav').addClass('glow-pink-txt'); 
  //$('#calendar_nav').addClass('glow'); 
  
  var cur_week = 0;
  var picture_days = [1, 9, 13, 19, 27];
  
  $.getJSON("{{ asset('json/recipes.json') }}", function(data) {
    var recipes = data;
    for (var i = 0; i < 5; i++) {
      var row = $("#calendar").append('<div class="week">');
      for (var j = 1; j < 8; j++) {
        var cell = '<div class="day">';
        cell += '<span class="day">' + ((cur_week + j) > 31 ? '' : (cur_week + j)) + '</span>';
        if (picture_days.indexOf(cur_week + j) != -1) {
          var index = Math.floor(Math.random() * recipes.length);
          var recipe = recipes[index];
          cell += '<div class="helper">';
          cell += '<a href="dishes/' + index + '">';
          cell += '<img width=128 align="top" src=' + recipe.image + '></img>';
          cell += '</a>'; 
          cell += '</div>';
        }
        cell += '</div>';
        row.append(cell);
      }
      row.after('</div>');
      cur_week += 7;
    }
  });
</script>
@endsection
