import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PiFiltros {


    public BufferedImage abreImagem() throws IOException {
        BufferedImage image = ImageIO.read(new File("Imagens-imput\\3.jpg"));
        return image;
    }
    public int[][] Image_To_Matriz(BufferedImage ImagemCarregada){
        int altura, largura;
        altura = ImagemCarregada.getHeight();
        largura = ImagemCarregada.getWidth();
        int vetAuxPixel[] = new int[largura*altura];
        vetAuxPixel = ImagemCarregada.getRGB(0, 0, largura, altura, null, 0, largura);
        int matrizPixel[][] = new int[altura][largura];
        int count = 0;
        for (int i=0;i<altura;i++)
        {
            for (int j=0;j<largura;j++)
            {
                matrizPixel[i][j]= vetAuxPixel[count];
                count++;
            }
        }
        return matrizPixel;
    }
    public void Cria_Imagem_Alterada(int [][] MatrizPixel,String NomeImagem) throws IOException{
        int new_altura,new_largura;
        new_altura=MatrizPixel.length;
        new_largura=MatrizPixel[1].length;
        int[] vetAux= new int[new_largura*new_altura];
        int count=0;
        for (int i=0;i<new_altura;i++){
            for(int j=0;j<new_largura;j++){
                vetAux[count]=MatrizPixel[i][j];
                count++;
            }
        }
        BufferedImage new_image_altera = new BufferedImage(new_largura, new_altura,BufferedImage.TYPE_INT_RGB);
        new_image_altera.setRGB(0, 0, new_largura, new_altura, vetAux, 0, new_largura);
        ImageIO.write(new_image_altera,"JPG", new File(NomeImagem+".jpg"));
    }
    int[][] maskSobel(int mask){
        int [][] SobelHorizontal = new int [3][3];
        SobelHorizontal[0][0] = -1;
        SobelHorizontal[0][1] = -2;
        SobelHorizontal[0][2] = -1;
        SobelHorizontal[2][0] = 1;
        SobelHorizontal[2][1] = 2;
        SobelHorizontal[2][2] = 1;

        int [][] SobelVertical = new int [3][3];
        SobelVertical[0][0] = -1;
        SobelVertical[1][0] = -2;
        SobelVertical[2][0] = -1;
        SobelVertical[0][2] = 1;
        SobelVertical[1][2] = 2;
        SobelVertical[2][2] = 1;
        switch(mask){
            case 1:
                return SobelHorizontal;
            case 2:
                return SobelVertical;
            default:
                return null;
        }
    }
    int[][] maskLaplaciano(int mask){
        int[][] mask1 = new int[3][3];
        int[][] mask2 = new int[3][3];
        int[][] mask3 = new int[3][3];
        int[][] mask4 = new int[3][3];
        mask1[0][0]=0;
        mask1[0][1]=1;
        mask1[0][2]=0;
        mask1[1][0]=1;
        mask1[1][1]=-4;
        mask1[1][2]=1;
        mask1[2][0]=0;
        mask1[2][1]=1;
        mask1[2][2]=0;
        mask2[0][0]=1;
        mask2[0][1]=1;
        mask2[0][2]=1;
        mask2[1][0]=1;
        mask2[1][1]=-8;
        mask2[1][2]=1;
        mask2[2][0]=1;
        mask2[2][1]=1;
        mask2[2][2]=1;
        mask3[0][0]=0;
        mask3[0][1]=-1;
        mask3[0][2]=0;
        mask3[1][0]=-1;
        mask3[1][1]=4;
        mask3[1][2]=-1;
        mask3[2][0]=0;
        mask3[2][1]=-1;
        mask3[2][2]=0;
        mask4[0][0]=-1;
        mask4[0][1]=-1;
        mask4[0][2]=-1;
        mask4[1][0]=-1;
        mask4[1][1]=8;
        mask4[1][2]=-1;
        mask4[2][0]=-1;
        mask4[2][1]=-1;
        mask4[2][2]=-1;
        switch(mask){
            case 1:
                return mask1;
            case 2:
                return mask2;  
            case 3:
                return mask3;
            case 4:
                return mask4;
            default:
                return null;
        }
    }
    int[][] maskMedia(int mask){
        int[][] mask1 = new int[3][3];
        int[][] mask2 = new int[3][3];
        mask1[0][0]=1;
        mask1[0][1]=1;
        mask1[0][2]=1;
        mask1[1][0]=1;
        mask1[1][1]=1;
        mask1[1][2]=1;
        mask1[2][0]=1;
        mask1[2][1]=1;
        mask1[2][2]=1;
        mask2[0][0]=1;
        mask2[0][1]=2;
        mask2[0][2]=1;
        mask2[1][0]=2;
        mask2[1][1]=4;
        mask2[1][2]=2;
        mask2[2][0]=1;
        mask2[2][1]=2;
        mask2[2][2]=1;
        switch(mask){
            case 1:
                return mask1;
            case 2:
                return mask2;
            default:
                return null;
        }
    }
    public int[][] Aplicar_Filtro(int altura, int largura, int [][]Matriz, int[][] filtro){
        int p1, p2, p3, p4, p5, p6, p7, p8, p9, p;
        int [][] Matriz2 = new int[altura][largura];
        for (int x = 1; x < altura - 1; x++) {
            for (int y = 1; y < largura - 1; y++) {
                p1 = Matriz[x-1][y-1]*filtro[0][0];
                p2 = Matriz[x-1][y]*filtro[0][1];
                p3 = Matriz[x-1][y+1]*filtro[0][2];
                p4 = Matriz[x][y-1]*filtro[1][0];
                p5 = Matriz[x][y]*filtro[1][1];
                p6 = Matriz[x][y+1]*filtro[1][2];
                p7 = Matriz[x+1][y-1]*filtro[2][0];
                p8 = Matriz[x+1][y]*filtro[2][1];
                p9 = Matriz[x+1][y+1]*filtro[2][2];
                p = p1+p2+p3+p4+p5+p6+p7+p8+p9;
                p = Math.min(255, Math.max(0, p));
                Matriz2[x][y] = 255 - p;
            }
        }
        return Matriz2;
    }
    public int[][] Aplicar_FiltroMedia(int altura, int largura, int [][]Matriz,int mask){
        int p1, p2, p3, p4, p5, p6, p7, p8, p9, p;
        int [][] Matriz2 = new int[altura+1][largura+1];
        int[][] MatrizAux = new int[altura+1][largura+1];
        for(int i = 0;i<MatrizAux.length;i++){
            for(int j=0; j<MatrizAux[1].length;j++){
                MatrizAux[i][j]=0;
            }
        }
        for(int i = 1;i<MatrizAux.length-1;i++){
            for(int j=1; j<MatrizAux[1].length-1;j++){
                MatrizAux[i][j]=Matriz[i-1][j-1];
            }
        }
        for (int x = 1; x < altura - 1; x++) {
            for (int y = 1; y < largura - 1; y++) {
                
                if(mask==1){
                    int[][] filtro = maskMedia(mask);
                    p1 = MatrizAux[x-1][y-1]*filtro[0][0];
                    p2 = MatrizAux[x-1][y]*filtro[0][1];
                    p3 = MatrizAux[x-1][y+1]*filtro[0][2];
                    p4 = MatrizAux[x][y-1]*filtro[1][0];
                    p5 = MatrizAux[x][y]*filtro[1][1];
                    p6 = MatrizAux[x][y+1]*filtro[1][2];
                    p7 = MatrizAux[x+1][y-1]*filtro[2][0];
                    p8 = MatrizAux[x+1][y]*filtro[2][1];
                    p9 = MatrizAux[x+1][y+1]*filtro[2][2];
                    p = (p1+p2+p3+p4+p5+p6+p7+p8+p9);
                    p = Math.min(255, Math.max(0, p)/9);
                    Matriz2[x][y] =p;
                }
                if(mask==2){
                    int[][] filtro = maskMedia(mask);
                    p1 = MatrizAux[x-1][y-1]*filtro[0][0];
                    p2 = MatrizAux[x-1][y]*filtro[0][1];
                    p3 = MatrizAux[x-1][y+1]*filtro[0][2];
                    p4 = MatrizAux[x][y-1]*filtro[1][0];
                    p5 = MatrizAux[x][y]*filtro[1][1];
                    p6 = MatrizAux[x][y+1]*filtro[1][2];
                    p7 = MatrizAux[x+1][y-1]*filtro[2][0];
                    p8 = MatrizAux[x+1][y]*filtro[2][1];
                    p9 = MatrizAux[x+1][y+1]*filtro[2][2];
                    p = (p1+p2+p3+p4+p5+p6+p7+p8+p9);
                    p = Math.min(255, Math.max(0, p)/16);
                    Matriz2[x][y] =p;
                }
            }
        }
        return Matriz2;
    }
    public void Sobel(BufferedImage Imagem1,int filtro) throws IOException {
        int altura = Imagem1.getHeight();
        int largura = Imagem1.getWidth();
        int count = 0;

        int[] VetRed = new int[largura*altura];
        int[] VetGreen = new int[largura*altura];
        int[] VetBlue = new int[largura*altura];

        for(int i=0;i<altura;i++){
            for(int j=0;j<largura;j++){
                Color color = new Color(Imagem1.getRGB(j, i));
                VetRed[count]=color.getRed();
                VetBlue[count]=color.getBlue();
                VetGreen[count]=color.getGreen();
                count++;
            }
        }
        count=0;
        int [][] MatrizRed = new int[altura][largura];
        int [][] MatrizGreen = new int[altura][largura];
        int [][] MatrizBlue = new int[altura][largura];
        int [][] MatrizRed2 = new int[altura][largura];
        int [][] MatrizGreen2 = new int[altura][largura];
        int [][] MatrizBlue2 = new int[altura][largura];

        for(int i=0;i<altura;i++){
            for(int j=0;j<largura;j++){
                MatrizRed[i][j]=VetRed[count];
                MatrizGreen[i][j]=VetGreen[count];
                MatrizBlue[i][j]=VetBlue[count];
                count++;
            }
        }

        MatrizRed2 = Aplicar_Filtro(altura, largura, MatrizRed, maskSobel(filtro));
        MatrizGreen2 = Aplicar_Filtro(altura, largura, MatrizGreen, maskSobel(filtro));
        MatrizBlue2 = Aplicar_Filtro(altura, largura, MatrizBlue, maskSobel(filtro));

        BufferedImage aux = new BufferedImage(Imagem1.getWidth(), Imagem1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for(int i=0;i<Imagem1.getWidth() ;i++){
            for(int j=0;j<Imagem1.getHeight();j++){
                Color color = new Color(MatrizRed2[j][i], MatrizGreen2[j][i], MatrizBlue2[j][i]);
                aux.setRGB(i, j, color.getRGB());
            }
        }
        Cria_Imagem_Alterada(Image_To_Matriz(aux), "sobel"+filtro);
    }
    public void Media(BufferedImage Imagem1,int filtro) throws IOException {
        int altura = Imagem1.getHeight();
        int largura = Imagem1.getWidth();
        int count = 0;

        int[] VetRed = new int[largura*altura];
        int[] VetGreen = new int[largura*altura];
        int[] VetBlue = new int[largura*altura];

        for(int i=0;i<altura;i++){
            for(int j=0;j<largura;j++){
                Color color = new Color(Imagem1.getRGB(j, i));
                VetRed[count]=color.getRed();
                VetBlue[count]=color.getBlue();
                VetGreen[count]=color.getGreen();
                count++;
            }
        }
        count=0;
        int [][] MatrizRed = new int[altura][largura];
        int [][] MatrizGreen = new int[altura][largura];
        int [][] MatrizBlue = new int[altura][largura];
        int [][] MatrizRed2 = new int[altura][largura];
        int [][] MatrizGreen2 = new int[altura][largura];
        int [][] MatrizBlue2 = new int[altura][largura];

        for(int i=0;i<altura;i++){
            for(int j=0;j<largura;j++){
                MatrizRed[i][j]=VetRed[count];
                MatrizGreen[i][j]=VetGreen[count];
                MatrizBlue[i][j]=VetBlue[count];
                count++;
            }
        }

        MatrizRed2 = Aplicar_FiltroMedia(altura, largura, MatrizRed,filtro);
        MatrizGreen2 = Aplicar_FiltroMedia(altura, largura, MatrizGreen,filtro);
        MatrizBlue2 = Aplicar_FiltroMedia(altura, largura, MatrizBlue,filtro);

        BufferedImage aux = new BufferedImage(Imagem1.getWidth(), Imagem1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for(int i=0;i<Imagem1.getWidth() ;i++){
            for(int j=0;j<Imagem1.getHeight();j++){
                Color color = new Color(MatrizRed2[j][i], MatrizGreen2[j][i], MatrizBlue2[j][i]);
                aux.setRGB(i, j, color.getRGB());
            }
        }
        Cria_Imagem_Alterada(Image_To_Matriz(aux), "Media"+filtro);
    }
    public void laplaciano(int[][] MatrizOld,int filtro) throws IOException {
        int altura = MatrizOld.length;
        int largura = MatrizOld[1].length;
        int count = 0;
        int[] VetRed = new int[largura*altura];
        int[] VetGreen = new int[largura*altura];
        int[] VetBlue = new int[largura*altura];
        for(int i=0;i<altura;i++){
            for(int j=0;j<largura;j++){
                Color color = new Color(MatrizOld[i][j]);
                VetRed[count]=color.getRed();
                VetBlue[count]=color.getBlue();
                VetGreen[count]=color.getGreen();
                count++;
            }
        }
        count=0;
        int [][] MatrizRed = new int[altura][largura];
        int [][] MatrizGreen = new int[altura][largura];
        int [][] MatrizBlue = new int[altura][largura];
        int [][] MatrizRed2;
        int [][] MatrizGreen2;
        int [][] MatrizBlue2;
        for(int i=0;i<altura;i++){
            for(int j=0;j<largura;j++){
                MatrizRed[i][j]=VetRed[count];
                MatrizGreen[i][j]=VetGreen[count];
                MatrizBlue[i][j]=VetBlue[count];
                count++;
            }
        }
        MatrizRed2 = Aplicar_Filtro(altura, largura, MatrizRed, maskLaplaciano(filtro));
        MatrizBlue2 = Aplicar_Filtro(altura, largura, MatrizBlue, maskLaplaciano(filtro));
        MatrizGreen2 = Aplicar_Filtro(altura, largura, MatrizGreen, maskLaplaciano(filtro));
        BufferedImage aux = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0;i<aux.getHeight()-1;i++){
            for(int j = 0;j<aux.getWidth()-1;j++){
                Color color = new Color(MatrizRed2[i][j], MatrizGreen2[i][j], MatrizBlue2[i][j]);
                aux.setRGB(j, i, color.getRGB());
            }
        }
        Cria_Imagem_Alterada(Image_To_Matriz(aux), "Laplaciano"+filtro);
    }
    public void run() throws IOException{
        laplaciano(Image_To_Matriz(abreImagem()), 1);
        laplaciano(Image_To_Matriz(abreImagem()), 2);
        laplaciano(Image_To_Matriz(abreImagem()), 3);
        laplaciano(Image_To_Matriz(abreImagem()), 4);
        Sobel(abreImagem(), 1);
        Sobel(abreImagem(), 2);
        Media(abreImagem(), 1);
        Media(abreImagem(), 2);
    }
    public static void main(String[] args) throws IOException {
        new PiFiltros().run();
    }
}