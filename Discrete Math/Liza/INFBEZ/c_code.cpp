#include<cstdio>
using namespace std;

int main()
{
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    char c;
    int d = 5; //����� � �����
    while(scanf("%c", &c) > 0)
    {
        if (c >= '�' && c <= '�')
        {
            int pos = c - '�';
            pos = (pos + d) % 33;
            c = pos + '�';
        }
        if (c >= '�' && c <= '�')
        {
            int pos = c - '�';
            pos = (pos + d) % 33;
            c = pos + '�';
        }
        printf("%c", c);    
    }
    return 0;
}

