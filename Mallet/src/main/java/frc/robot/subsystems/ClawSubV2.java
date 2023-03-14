package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.K_ClawSub;;

public class ClawSubV2 extends SubsystemBase{
  // This is the Claw Extension Motor
  // Idle - Break
  // ID - 8
  private final CANSparkMax motor = new CANSparkMax(5, MotorType.kBrushless);
  private final RelativeEncoder encoder = motor.getEncoder();
  
  // Limit Switch
  // WARNING - MAKE SURE THE LIMITS ARE HAVING THE YELLOW IN GROUND!
  //           YES IT LOOKS WRONG BUT BLAME ELECTRICAL FOR THEIR WIRING!
  //           --> DEFAULT IS ALWAYS TRUE BUT WHEN HIT THEY RETURN FALSE!
  private final DigitalInput clampLimit = new DigitalInput(4);

  // Calculation variables
  private double desiredAngle;
  // open further than default point
  private double minAngle = 0;
  // close close to full clamp point
  private double maxAngle = 30;

  private boolean isStopped = false;
  
  public ClawSubV2(){
    if(K_ClawSub.isUsingClaw){
      motor.setIdleMode(IdleMode.kBrake);
      //motor.setInverted(true);
      // set conversion factor so getPosition returns degrees
      encoder.setPositionConversionFactor((K_ClawSub.calibrateEndingAngle-K_ClawSub.calibrateStartingAngle) / K_ClawSub.calibrateAngleEncoderValue);
      desiredAngle = 15;
      //desiredAngle = encoder.getPosition();
      //minAngle = desiredAngle;
      //maxAngle = desiredAngle + maxAngle;
    }
  }

  // Handles motor movement
  // Adjusts voltage / motor speed based on difference between current and desired angle
  // Maintains 
  public void moveMotors(){
    if(isStopped)
      emergencyStop();
    else{
      //Calculates voltage based on the distance between encoders
      double calculatedVoltage = (desiredAngle - encoder.getPosition());
      //Makes sure encoders is not too far or too back
      if (calculatedVoltage > K_ClawSub.clampSpeed) {calculatedVoltage = K_ClawSub.clampSpeed;}
      if (calculatedVoltage < -K_ClawSub.clampSpeed) {calculatedVoltage = -K_ClawSub.clampSpeed;}
      // Dead zone for voltage
      if (Math.abs(calculatedVoltage)<0.01) calculatedVoltage = 0;
      //???Checks if the limit switches is not pressed along with the calculated voltage is going backwards or if the calculated voltage is going forwards
      if ((calculatedVoltage < 0 && clampLimit.get()) || calculatedVoltage > 0) {
        motor.setVoltage(calculatedVoltage);
      } else {
        motor.setVoltage(0);
        //Checks whether or is too open or too close and sets the current position to max or min angle 
        if (!clampLimit.get())
          maxAngle = encoder.getPosition();
        else
          minAngle = encoder.getPosition();
      }
    }
  }

  public void clamp() {
    //If the motor is not met with resistence (aka not being closing sometihing) and it can still go backwards
    if (motor.getOutputCurrent() < K_ClawSub.maxCurrent && encoder.getPosition() > minAngle) {
      motor.setVoltage(-.5);
    } 
    else {
      desiredAngle = encoder.getPosition();
    }
  
  }

  public void setAngle (double angle) {
    desiredAngle = angle;
  }

  // Changes angle to aim for
  // If change is too far in either direction revert the change
  public void changeAngle (double increment) {
    SmartDashboard.putNumber("Increment",increment);
    //???If the amount is too big, then make it +- .5 
    if(Math.abs(increment)>0.5)
      increment = 0.5*Math.abs(increment)/increment;
    //If the increment amount is too little then disregarded it
    if(Math.abs(increment)<0.1)
      increment = 0;
    //Increments
    desiredAngle += increment;
    //Clamps the angle
    if (desiredAngle > maxAngle) 
      desiredAngle= maxAngle;
    if (desiredAngle < minAngle) 
      desiredAngle= minAngle;
  }

  public double getCurrent() {
    return motor.getOutputCurrent();
  }

  public void zeroEncoder() {
    encoder.setPosition(0);
  }

  // Stops the motor in case of emergency
  public void emergencyStop(){
    motor.stopMotor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Claw Encoder", encoder.getPosition());
    SmartDashboard.putNumber("Claw Current", motor.getOutputCurrent());
    SmartDashboard.putNumber("Desired Angle", desiredAngle);
    SmartDashboard.putNumber("Min Angle", minAngle);
    SmartDashboard.putNumber("Max Angle",maxAngle);
  }
}
