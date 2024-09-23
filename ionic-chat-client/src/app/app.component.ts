import {AfterViewInit, Component, ElementRef, ViewChild} from '@angular/core';
import {ChatService} from "./service/chat.service";
import {IonContent} from "@ionic/angular";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
  providers: [ChatService]
})
export class AppComponent implements AfterViewInit{

  // @ViewChild(IonContent, { static: false }) content: IonContent;
  @ViewChild(IonContent) content!: IonContent;
  @ViewChild('messageEnd', { static: false }) messageEnd!: ElementRef;
  messages: { text: string, isSent: boolean, suggestionList?: Array<any> }[] = [];
  newMessage: string = '';
  public enableSend: boolean = true;

  constructor(private chatService: ChatService) {
    this.enableSend = true;
  }

  sendMessage() {
    if (this.newMessage.trim() !== '') {
      // Add the sent message to the chat
      this.messages.push({text: this.newMessage, isSent: true});
      this.scrollToLastMessage();

      this.chatService.sendMessage(this.newMessage).subscribe(
        response => {
          console.log('Message sent successfully:', response);
          this.messages.push({text: response.message, isSent: false, suggestionList: response.suggestionList});
        },
        error => {
          console.error('Error sending message:', error);
          this.messages.push({text: 'Please try again after sometime', isSent: false});
        },
        () => {
          this.enableSend = true;
          this.scrollToLastMessage();
        }
      );

      // Clear the input field
      this.newMessage = '';

      setTimeout(() => {
        this.messages.push({text: 'This is an auto-reply', isSent: false});
      }, 1000);
    }
  }

  public onMessageChange() {
    this.enableSend = this.newMessage.trim().length > 0;
  }

  public orderNow(restaurant: any): void {

  }


  scrollToLastMessage() {
    if (this.messageEnd) {
      setTimeout(() => {
        // this.content.scrollToBottom(1000);
        this.messageEnd.nativeElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
      }, 200); // Adding a small delay to ensure UI updates before scrolling
    }

  }

  ngAfterViewInit() {
    this.scrollToLastMessage(); // Ensure scrolling to the last message
  }
}
