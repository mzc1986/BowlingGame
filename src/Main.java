import java.util.Scanner;

public class Main {

    static int FRAME_COUNT = 10;
    static int BALL_COUNT = FRAME_COUNT * 2 + 1;
    static int PERFECT_SCORE = 10;
    static int STRIKE_FRAME_SECOND_BALL = 11;

    public static void main(String[] args) {

        int[] fType = new int[FRAME_COUNT];
        int[] fScore = new int[FRAME_COUNT];
        int ball[] = new int[BALL_COUNT];

        int j = 0;

        Scanner s=new Scanner(System.in);
        System.out.println("enter ball scores:");

        /*Test inputs:
        1 4 4 5 6 4 5 5 10 0 1 7 3 6 4 10 2 8 6
        10 10 10 10 10 10 10 10 10 10 10 10
        1 4 4 5 10 5 5 10 0 1 7 3 6 4 10 2 8 6
        10 10 5 5 10 10 10 10 10 10 10 10 10
        10 10 5 4 10 10 10 10 10 10 10 10 10

        Test outputs:
        5 14 29 49 60 61 77 97 117 133
        30 60 90 120 150 180 210 240 270 300
        5 14 34 54 65 66 82 102 122 138
        25 45 65 95 125 155 185 215 245 275
        25 44 53 83 113 143 173 203 233 263
        */

        for(int i=0;i<BALL_COUNT;){
            int a = s.nextInt();

            //Adjustment for Strike frame second ball
            if(a == PERFECT_SCORE && i % 2 ==0 && i < BALL_COUNT - 3)
            {
                ball[i] = a;
                ball[i+1] = STRIKE_FRAME_SECOND_BALL;
                i= i + 2;
            } else {
                ball[i] = a;
                i++;
            }
        }

        /*for(int i=0;i<BALL_COUNT;i++){
            System.out.print(ball[i] + " ");
        }*/

        System.out.println();

        //Classify Frame Type
        for(int i=0; i< FRAME_COUNT; i++){
            j = i*2;
            if(ball[j] == PERFECT_SCORE) {
                fType[i] = 2; //Strike
                continue;
            }
            if(ball[j] + ball[j+1] == PERFECT_SCORE)
            {
                fType[i] = 1; //Spare
                continue;
            }
            if(ball[j] + ball[j+1] < PERFECT_SCORE) {
                fType[i] = 0; //Nothing
                continue;
            }
        }

        // Calculate Score for each Frame
        for(int i=0; i< FRAME_COUNT; i++) {
            j = i*2;
            if(fType[i]==0) // Nothing
            {
                if(i>0 && i < 9)
                    fScore[i] = fScore[i-1] + getNextTwoBallScores(ball, j);
                else if(i==0)
                    fScore[i] = getNextTwoBallScores(ball, j);
                else if(i==9)
                    fScore[i] = fScore[i-1] + getTenthScore(ball, j);
            }
            if(fType[i]==1) // Spare
            {
                if(i > 0 && i < 9)
                    fScore[i] = fScore[i - 1] + 10 + getNextOneBallScore(ball, j+2);
                else if(i==0)
                    fScore[i] = 10 + getNextOneBallScore(ball, j+2);
                else if(i==9)
                    fScore[i] = fScore[i-1] + getTenthScore(ball, j);
            }
            if(fType[i]==2) // Strike
            {
                if(i > 0 && i < 9)
                    fScore[i] = fScore[i - 1] + 10 + getNextTwoBallScores(ball, j+2) ;
                else if(i==0)
                    fScore[i] = 10 + getNextTwoBallScores(ball, j+2);
                else if(i==9)
                    fScore[i] = fScore[i-1] + getTenthScore(ball, j);
            }

        }

        for (int score : fScore) {
            System.out.print( score + " ");
        }
    }

    static int getNextTwoBallScores(int[] b, int k){
        int count = 0;
        int nextTwoBallScore = 0;

        while(k < BALL_COUNT  ){
            if(b[k]!= STRIKE_FRAME_SECOND_BALL) {
                nextTwoBallScore += b[k];
                count++;
                if(count == 2)
                    break;
            }
            k++;
        }
        return nextTwoBallScore;
    }

    static int getNextOneBallScore(int[] b, int k){
        int count = 0;
        int nextTwoBallScore = 0;

        while(k < BALL_COUNT  ){
            if(b[k]!= STRIKE_FRAME_SECOND_BALL) {
                nextTwoBallScore += b[k];
                count++;
                if(count == 1)
                    break;
            }
            k++;
        }
        return nextTwoBallScore;
    }

    static int getTenthScore(int[] b, int k){

        int nextTwoBallScore = 0;

        //maybe scores
        if(b[k] != STRIKE_FRAME_SECOND_BALL)
            nextTwoBallScore += b[k];
        if(b[k+1] != STRIKE_FRAME_SECOND_BALL)
            nextTwoBallScore += b[k+1];
        if(b[k+2]!= STRIKE_FRAME_SECOND_BALL)
            nextTwoBallScore += b[k+2];

        return nextTwoBallScore;
    }

}
