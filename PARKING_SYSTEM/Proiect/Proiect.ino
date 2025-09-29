#include <Servo.h> //controleaza servomotorul
#include <Wire.h>
#include <LiquidCrystal_I2C.h> //controleaza afisajul LCD cu protocolul I2C 
LiquidCrystal_I2C lcd(0x27, 16, 2); //permite utilizarea unui display LCD cu interfata I2C
//0x27->este adresa I2C a modulului LCD,16->caractere pe linie,2->linii

Servo myservo; //controleaza deschiderea si inchiderea barierei

#define ir_enter 2 //senzor de intrare, detecteaza vehiculele care intra
#define ir_back 4 //senzor de iesire, detecteaza vehiculele care ies
//senzori pentru locurile de parcare, detecteaza daca locurile sunt ocupate sau libere
#define ir_car1 5
#define ir_car2 6
#define ir_car3 7
#define ir_car4 8


int S1 = 0, S2 = 0, S3 = 0, S4 = 0;
int flag1 = 0, flag2 = 0; //indicatori pentru controlul logicii barierei
int slot = 4;

void setup() {
  Serial.begin(9600);
  //se configureaza pinii senzorilor ca intrari
  pinMode(ir_car1, INPUT);
  pinMode(ir_car2, INPUT);
  pinMode(ir_car3, INPUT);
  pinMode(ir_car4, INPUT);
  pinMode(ir_enter, INPUT);
  pinMode(ir_back, INPUT);
  myservo.attach(3); //conecteaza servomotorul la pinul 3
  myservo.write(90); //servo-motorul este pozitionat la 90 de grade(bariera inchisa)
  lcd.init(); //initializeaza LCD-ul
  lcd.backlight(); //porneste iluminarea de fundal
  lcd.setCursor(0, 0); //pozitioneaza cursorul la inceputul liniei 1
  lcd.print("Welcome to JDE");
  delay(2000);
  lcd.setCursor(0, 1); //pozitioneaza cursorul la inceputul liniei 1
  lcd.print("Car Parking Sys");
  delay(3000);
  lcd.clear(); //sterge tot continutul de pe ecran
  Read_Sensor();
  int total = S1 + S2 + S3 + S4;
  slot = slot - total;
}

void loop() {
  Read_Sensor();
  lcd.setCursor(0, 0);
  lcd.print("Slots: ");
  lcd.print(slot);
  lcd.print("   "); // stergem restul caracterelor

  lcd.setCursor(0, 1);
  lcd.print(S1 == 1 ? "S1:F" : "S1:E");
  lcd.print(S2 == 1 ? "S2:F" : "S2:E");
  lcd.print(S3 == 1 ? "S3:F" : "S3:E");
  lcd.print(S4 == 1 ? "S4:F" : "S4:E");

  if (digitalRead(ir_enter) == 0 && flag1 == 0) {//se verifica daca senzorul detecteaza o masina si daca nu au fost deja efectuate actiuni de intrare
    if (slot > 0) { // daca mai este loc in parcare
      flag1 = 1;
      if (flag2 == 0) {
        delay(300);
        myservo.write(180); // se deschide bariera
        slot = slot - 1; // scade numarul de locuri
      }
    } else {
      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("Parking Full");
      delay(1500);
      lcd.clear();
    }
  }
  if (digitalRead(ir_back) == 0 && flag2 == 0) {
    flag2 = 1;
    if (flag1 == 0) {
      delay(300);
      myservo.write(180);
      slot = slot + 1;
       if (slot > 4) {  // Prevenire crestere peste numrul maxim de sloturi
      slot = 4;
    }
    }
    delay(1000);
  }

  if (flag1 == 1 && flag2 == 1) { //masina a trecut de ambii senzori
    delay(1000);
    myservo.write(90);// se inchide bariera
    flag1 = 0, flag2 = 0;
  }

  delay(1);
}

void Read_Sensor() {
  S1 = 0, S2 = 0, S3 = 0, S4 = 0;
  if (digitalRead(ir_car1) == 0) {
    S1 = 1;
  }
  if (digitalRead(ir_car2) == 0) {
    S2 = 1;
  }
  if (digitalRead(ir_car3) == 0) {
    S3 = 1;
  }
  if (digitalRead(ir_car4) == 0) {
    S4 = 1;
  }
}
