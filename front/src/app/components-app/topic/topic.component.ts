import { Component, OnInit } from '@angular/core';
import { Topics } from 'src/app/interfaces/topics.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss']
})
export class TopicComponent implements OnInit {
  myTopics:Topics[] = [];

  constructor(private userService:UserService) { }


  ngOnInit(): void {
    this.userService.getUserMe().subscribe((res) => { this.myTopics = res.topics; });
  }

}
