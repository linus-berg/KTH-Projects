/* mipslabwork.c

   This file written 2015 by F Lundevall
   Updated 2017-04-21 by F Lundevall

   This file should be changed by YOU! So you must
   add comment(s) here with your name(s) and date(s):

   This file modified 2017-04-31 by Ture Teknolog 

   For copyright and licensing, see file COPYING */

#include <stdint.h>   /* Declarations of uint_32 and the like */
#include <pic32mx.h>  /* Declarations of system-specific addresses etc */
#include "mipslab.h"  /* Declatations for these labs */

int mytime = 0x5957;

char textstring[] = "text, more text, and even more text!";
int setTime(int time_c, int push);
/* Interrupt Service Routine */
void user_isr( void )
{
  return;
}

/* Lab-specific initialization goes here */
void labinit( void )
{
  *(volatile unsigned*)(0xBF886100) &= ~(0xFF);
  *(volatile unsigned*)(0xBF886110) = 0;
  TRISD |= 0xFE0;
  return;
}

/* This function is called repetitively from the main program */
void labwork( void )
{
  delay( 1000 );
  mytime = setTime(mytime, 2);
  mytime = setTime(mytime, 3);
  mytime = setTime(mytime, 4);
  time2string( textstring, mytime );
  display_string( 3, textstring );
  display_update();
  tick( &mytime );
  *(volatile unsigned*)(0xBF886110) = *(volatile unsigned*)(0xBF886110) + 1 % 128;
  display_image(96, icon);
}

int setTime(int time_c, int push) {
  push--;
  if (getbtns() >> (push - 1) & 0x1) {
    return (time_c & ~(0xF << (push * 4))) | (getsw() << (push * 4)); 
  } else {
    return time_c;
  }
}
