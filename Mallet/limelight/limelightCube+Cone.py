import cv2
import numpy as np

# runPipeline() is called every frame by Limelight's backend.
def runPipeline(image, llrobot):
    # define center of image
    cX = 160
    cY = 120

    # define extra limelight array
    llpython = [0, 0, 0, 0, 0, 0, 0, 0]

    # convert image to hsv
    img_hsv = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)

    # find cube contours
    cube_thresh = cv2.inRange(img_hsv, (107, 97, 105), (131, 255, 255))
    cube_contours, _ = cv2.findContours(cube_thresh,
                                   cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    
    # find cone contours
    cone_thresh = cv2.inRange(img_hsv, (22, 170, 190), (35, 255, 255))
    cone_contours, _ = cv2.findContours(cone_thresh,
                                   cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    # get largest contour in cone contours if it exists
    largestCone = np.array([[]])
    if len(cone_contours) > 0:
        largestCone = max(cone_contours, key=cv2.contourArea)

    # get largest contour in cube contours if it exists
    largestCube = np.array([[]])
    if len(cube_contours) > 0:
        largestCube = max(cube_contours, key=cv2.contourArea)

    # get largest contour between the two
    largestContour = np.array([[]])
    if cv2.contourArea(largestCone) > cv2.contourArea(largestCube):
        largestContour = largestCone
    else:
        largestContour = largestCube

    # if area above threshold, find center of contour
    # otherwise set center to center of image
    if cv2.contourArea(largestContour) > 50:
        M = cv2.moments(largestContour)
        if M["m00"] != 0:
            cX = int(M["m10"] / M["m00"])
            cY = int(M["m01"] / M["m00"])
    ctr = np.array([[cX - 2, cY + 2], [cX + 2, cY + 2], [cX + 2, cY - 2], [cX - 2, cY - 2]]).reshape((-1, 1, 2)).astype(
        np.int32)

    # make sure to return a contour,
    # an image to stream,
    # and optionally an array of up to 8 values for the "llpython"
    # networktables array
    return ctr, image, llpython